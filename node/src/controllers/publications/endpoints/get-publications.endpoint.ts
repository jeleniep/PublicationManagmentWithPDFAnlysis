import { Request, Response, NextFunction } from 'express';
import Publication from "../publication.model";
import User, { UserType } from '../../users/user.model';
import { UserAccountNotExist } from '../../../exceptions';

const getPublications = async (req: Request, res: Response, next: NextFunction) => {
    const { _id, email } = (<any>req).user;
    let user: UserType;

    try {
        user = await User.findById(_id).select({ email: 1, username: 1});
    } catch (error) {
        return next(new UserAccountNotExist(email));
    }
    const publications = await Publication.find({owners: user});
    res.json(publications);
}

export default getPublications;
