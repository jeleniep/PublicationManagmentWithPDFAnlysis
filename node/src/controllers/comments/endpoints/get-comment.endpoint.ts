import { Request, Response, NextFunction } from 'express';
import Comment from "../comment.model";

const getComment = async (req: Request, res: Response, next: NextFunction) => {
    const { id } = req.params;
    const comment = await Comment.findById(id);
    res.json(comment);
}

export default getComment;
