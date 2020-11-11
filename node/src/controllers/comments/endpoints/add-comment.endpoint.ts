import { Request, Response, NextFunction } from 'express';
import Comment from '../comment.model';
import { PublicationNotExist, UserAccountNotExist } from '../../../exceptions';
import Publication, { PublicationType } from '../../publications/publication.model';
import User, { UserType } from '../../users/user.model';


const addComment = async (req: Request, res: Response, next: NextFunction) => {
    const { body, publicationId } = req.body;
    const { _id, email } = (<any>req).user;
    const comment = new Comment();
    comment.body = body;

    let publication: PublicationType;
    try {
        publication = await Publication.findById(publicationId);
    } catch (error) {
        return next(new PublicationNotExist(publicationId))
    }
    comment.publication = publication;

    let user: UserType;
    try {
        user = await User.findById(_id);
    } catch (error) {
        return next(new UserAccountNotExist(email));
    }
    comment.owners = [user];
    await comment.save();
    res.json(comment);
}

export default addComment;