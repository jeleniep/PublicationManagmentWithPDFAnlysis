import { Request, Response, NextFunction } from 'express';
import Comment from "../comment.model";

const getComments = async (req: Request, res: Response, next: NextFunction) => {
    const comments = await Comment.find();
    res.json(comments);
}

export default getComments;
