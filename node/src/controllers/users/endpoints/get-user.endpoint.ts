import { Request, Response, NextFunction } from 'express';
import only from 'only';
import User from '../user.model';
import { ADMIN } from '../../../constants';

const getUser = async (req: Request, res: Response, next: NextFunction) => {
    const { id } = req.params;
    const mongoId = id === 'me' ? (<any>req).user._id : id;
    const user = await User.findById(mongoId).select({ email: 1, username: 1, profile: 1, _id: 1 });
    return res.json({ ...user.toObject() });
}

export default getUser