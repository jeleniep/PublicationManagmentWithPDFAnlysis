import { Request, Response, NextFunction } from 'express';
import only from 'only';
import User from '../user.model';
import { SALT_ROUNDS } from '../../../config'
import bcrypt from 'bcrypt'
import crypto from 'crypto'

import { } from '../../../exceptions';
import { USER } from '../../../constants';

const registerUser = async (req: Request, res: Response, next: NextFunction) => {
    const { email, username, password } = req.body;
    const user = new User();
    user.email = (<string>email).toLowerCase();
    user.username = username;
    user.hash = await bcrypt.hash(password, SALT_ROUNDS);
    user.profile = USER
    await user.save();
    const reducedUser = only(user.toObject(), "email username profile _id token language")
    res.json({ ...reducedUser });
}

export default registerUser