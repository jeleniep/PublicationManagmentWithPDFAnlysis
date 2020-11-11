import { Request, Response, NextFunction } from 'express';
import Comment from "../comment.model";

const editComment = async (req: Request, res: Response, next: NextFunction) => {
    const { id } = req.params;
    const { body } = req.body;
    const comment = await Comment.findById(id);
    comment.body = body || comment.body;
    await comment.save();
    res.json(comment);
}

export default editComment;
