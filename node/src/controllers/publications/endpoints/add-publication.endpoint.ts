import { Request, Response, NextFunction } from 'express';
import Publication from '../publication.model';
import User, { UserType } from '../../users/user.model';
import { UserAccountNotExist } from '../../../exceptions';


const addPublication = async (req: Request, res: Response, next: NextFunction) => {
    const { name, description, authors } = req.body;
    const { _id, email } = (<any>req).user;
    let user: UserType;
    try {
        user = await User.findById(_id).select({ email: 1, username: 1});
    } catch (error) {
        return next(new UserAccountNotExist(email));
    }
    const publication = new Publication();
    publication.name = name;
    publication.description = description;
    publication.authors = authors;
    publication.owners = [user]
    await publication.save();
    res.json(publication);
}

export default addPublication;