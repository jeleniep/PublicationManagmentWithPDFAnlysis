
import { Router, Request, Response, NextFunction } from 'express';
import Controller from '../../interfaces/controller.interface';
import { wrapAsync } from '../../middleware';
import { createQueryBuilder } from 'typeorm'
import { promises as fs } from 'fs';
import pdf from 'pdf-parse'
import {
    getPublications,
    getPublication,
    addPublication,
    deletePublication,
    editPublication
} from './endpoints';

class PublicationsController implements Controller {
    public readonly path = '/publications';
    public readonly router = Router();
    constructor() {
        this.initializeRoutes();
    }

    private initializeRoutes() {
        const router = Router();
        router
            .get('/', wrapAsync(getPublications))
            .post('/', wrapAsync(addPublication))
            .get('/:id', wrapAsync(getPublication))
            .put('/:id', wrapAsync(editPublication))
            .delete('/:id', wrapAsync(deletePublication))


        this.router.use(
            this.path,
            router
        );
    }




    private getPublication = async (req: Request, res: Response, next: NextFunction) => {
        const file = await fs.readFile("opis.pdf");
        const pdfData = await pdf(file);
        res.json(pdfData);
    }



}

export default PublicationsController;
