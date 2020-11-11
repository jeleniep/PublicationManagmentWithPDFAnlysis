import { Request, Response, NextFunction } from 'express';
import User from '../user.model';


const editUser = async (req: Request, res: Response, next: NextFunction) => {
    const { id } = req.params;
    const { email, username, profile } = req.body;
    const user = await User.findById(id).select({ email: 1, username: 1, profile: 1 });
    user.email = email ? (<string>email).toLowerCase() : user.email;
    user.username = username || user.username;
    user.profile = profile || user.profile;

    await user.save();
    res.json(user);
}

export default editUser