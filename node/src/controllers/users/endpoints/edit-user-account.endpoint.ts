import { Request, Response, NextFunction } from 'express';
import only from 'only';
import User from '../user.model';
import { SALT_ROUNDS } from '../../../config'
import bcrypt from 'bcrypt'
import crypto from 'crypto'
import { IncorrectPasswordException, DifferentPasswordsException } from '../../../exceptions';

const editUserAccount = async (req: Request, res: Response, next: NextFunction) => {
    const { _id } = (<any>req).user;
    const { username, email, currentPassword, newPassword, newPasswordRepeated } = req.body;
    const user = await User.findById(_id);
    const authStatus = await bcrypt.compare(currentPassword, user.hash);
    if (authStatus) {
        user.username = username || user.username;
        user.email = email ? (<string>email).toLowerCase() : user.email;;
        if (newPassword) {
            if (newPassword !== newPasswordRepeated) {
                throw new DifferentPasswordsException();
            }
            user.hash = await bcrypt.hash(newPassword, SALT_ROUNDS);
        }
        await user.save();
        return res.json(only(user, "email username profile _id"));

    }
    throw new IncorrectPasswordException();
}

export default editUserAccount