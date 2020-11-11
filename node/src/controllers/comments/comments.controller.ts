
import { Router, Request, Response, NextFunction } from 'express';
import Controller from '../../interfaces/controller.interface';
import { wrapAsync, authMiddlewareUser, authMiddlewareOwner, validationMiddleware } from '../../middleware';
import { promises as fs } from 'fs';
import pdf from 'pdf-parse'
import {
    getComments,
    getComment,
    getCommentsByPublication,
    addComment,
    deleteComment,
    editComment
} from './endpoints';
import Comment from './comment.model'
import { AddCommentDto } from './dto';

class CommentsController implements Controller {
    public readonly path = '/comments';
    public readonly router = Router();
    constructor() {
        this.initializeRoutes();
    }

    private initializeRoutes() {
        const router = Router();
        router
            .get('/', authMiddlewareUser, wrapAsync(getComments))
            .post('/', authMiddlewareUser, validationMiddleware(AddCommentDto), wrapAsync(addComment))
            .get('/:id', authMiddlewareUser, wrapAsync(getComment))
            .put('/:id', authMiddlewareOwner(Comment), validationMiddleware(AddCommentDto), wrapAsync(editComment))
            .delete('/:id', authMiddlewareOwner(Comment), wrapAsync(deleteComment))
            .get('/byPublication/:id', authMiddlewareUser, wrapAsync(getCommentsByPublication))

        this.router.use(
            this.path,
            router
        );
    }




    private getComment = async (req: Request, res: Response, next: NextFunction) => {
        const file = await fs.readFile("opis.pdf");
        const pdfData = await pdf(file);
        res.json(pdfData);
    }



}

export default CommentsController;
