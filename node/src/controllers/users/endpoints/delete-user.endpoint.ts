import { Request, Response, NextFunction } from 'express';
import User, { UserType } from '../user.model';
import only from 'only';
import { USER } from '../../../constants';
import { UserAccountNotExist } from '../../../exceptions';
import logOutUser from './log-out-user.endpoint';


const deleteUser = async (req: Request, res: Response, next: NextFunction) => {
    const { id } = req.params;
    const { _id } = (<any>req).user;
    const mongoId = id === 'me' ? _id : id;
    let user: UserType;
    try {
        user = await User.findById(mongoId);
    } catch (error) {
        return next(new UserAccountNotExist(mongoId));
    }
    await User.findByIdAndDelete(mongoId);
    await logOutUser(req, res, next);
    return res.json(only(user, "email username profile _id"));
}

export default deleteUser