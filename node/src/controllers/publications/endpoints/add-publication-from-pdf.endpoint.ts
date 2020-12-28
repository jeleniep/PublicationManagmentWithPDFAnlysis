import { Request, Response, NextFunction } from 'express';
import Publication from '../publication.model';
import User, { UserType } from '../../users/user.model';
import { UserAccountNotExist } from '../../../exceptions';
import { extractDoi, getDetailsFromDoi } from '../../../helpers';
import pdfParser from 'pdf-parse';
import { promises as fs } from 'fs';
import { PATH_TO_PDF } from "../../../config"

const addPublicationFromPdf = async (req: Request, res: Response, next: NextFunction) => {
    const { name, description, authors, tags } = req.body;
    const { _id, email } = (<any>req).user;
    let user: UserType;
    try {
        user = await User.findById(_id).select({ email: 1, username: 1 });
    } catch (error) {
        return next(new UserAccountNotExist(email));
    }
    const publication = new Publication();
    const file = await fs.readFile(req.file.path);
    const pdfData = await pdfParser(file);
    publication.name = pdfData.info.Title;
    publication.authors = pdfData.info.Author ? [pdfData.info.Author] : [];
    publication.owners = [user]
    publication.file = `./${req.file.path}`;
    publication.doi = extractDoi(pdfData);
    const detailsFromDoi = await getDetailsFromDoi(publication.doi)
    if (detailsFromDoi) {
        publication.name = detailsFromDoi.name;

        console.log(detailsFromDoi)

    }
    return res.json(publication);
}

export default addPublicationFromPdf;