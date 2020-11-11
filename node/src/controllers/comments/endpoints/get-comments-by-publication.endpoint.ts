import { Request, Response, NextFunction } from 'express';
import Comment from "../comment.model";
import Publication from '../../publications/publication.model';

const getCommentsByPublication = async (req: Request, res: Response, next: NextFunction) => {
    const { id } = req.params;
    const publication = await Publication.findById(id);
    const comments = await Comment.find({publication});
    res.json(comments);
}

export default getCommentsByPublication;
