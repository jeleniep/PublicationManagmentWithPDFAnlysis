import { Request, Response, NextFunction } from 'express';
import Publication from "../publication.model";
import { PATH_TO_PDF } from '../../../config';
import { PublicationNotExist } from '../../../exceptions'
const getPublicationPdf = async (req: Request, res: Response, next: NextFunction) => {
    const { id } = req.params;
    const publication = await Publication.findById(id);
    if (publication) {
        const file = publication.file;
        return res.download(file);
    }
    return next(new PublicationNotExist(id))
}

export default getPublicationPdf;
