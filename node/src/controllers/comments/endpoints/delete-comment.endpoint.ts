import { Request, Response, NextFunction } from 'express';
import Comment from "../comment.model";

const deleteComment = async (req: Request, res: Response, next: NextFunction) => {
    const { id } = req.params;
    const comment = await Comment.findByIdAndDelete(id);
    res.json(comment);
}

export default deleteComment;
