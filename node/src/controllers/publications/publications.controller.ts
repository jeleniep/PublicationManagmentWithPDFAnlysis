
import { Router, Request, Response, NextFunction } from 'express';
import Controller from '../../interfaces/controller.interface';
import { wrapAsync, authMiddlewareUser, authMiddlewareOwner, validationMiddleware } from '../../middleware';
import { promises as fs } from 'fs';
import pdf from 'pdf-parse'
import multer from 'multer'
import {
    getPublications,
    getPublication,
    addPublication,
    addPublicationFromPdf,
    deletePublication,
    editPublication,
    getPublicationPdf
} from './endpoints';
import Publication from './publication.model';
import { AddPublicationDto, EditPublicationDto } from './dto';
import { PATH_TO_PDF } from "../../config"

const storage = multer.diskStorage(
    {
        destination: PATH_TO_PDF,
        filename: async function ( req, file, cb ) {
            const prefix = (<any>req).user._id
            const time = (new Date()).getTime()
            await fs.mkdir(`${PATH_TO_PDF}/${prefix}/temp/${time}`, { recursive: true })

            cb( null, `${prefix}/temp/${time}/${file.originalname}` );
        }
    }
  );
const upload = multer({
    storage
});


class PublicationsController implements Controller {
    public readonly path = '/publications';
    public readonly router = Router();
    constructor() {
        this.initializeRoutes();
    }

    private initializeRoutes() {
        const router = Router();
        router
            .get('/', authMiddlewareUser, wrapAsync(getPublications))
            // .get('/', wrapAsync(getPublications))
            .post('/', authMiddlewareUser, validationMiddleware(AddPublicationDto), wrapAsync(addPublication))
            .post('/fromPdf', authMiddlewareUser, upload.single("file"), wrapAsync(addPublicationFromPdf))
            // .get('/:id', authMiddlewareUser, wrapAsync(getPublication))
            .get('/:id', authMiddlewareOwner(Publication), wrapAsync(getPublication))
            .get('/:id/pdf', authMiddlewareOwner(Publication), wrapAsync(getPublicationPdf))
            .put('/:id', authMiddlewareOwner(Publication), validationMiddleware(EditPublicationDto), wrapAsync(editPublication))
            .delete('/:id', authMiddlewareOwner(Publication), wrapAsync(deletePublication))


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
