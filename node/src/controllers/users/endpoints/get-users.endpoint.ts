import { Request, Response, NextFunction } from 'express';
import only from 'only';
import User from '../user.model';

import { ADMIN } from '../../../constants';

const getUsers = async (req: Request, res: Response, next: NextFunction) => {
    const users = await User.find().select(
        {
            email: 1,
            username: 1, 
            profile: 1,
        });
    return res.json(users);
}

export default getUsers