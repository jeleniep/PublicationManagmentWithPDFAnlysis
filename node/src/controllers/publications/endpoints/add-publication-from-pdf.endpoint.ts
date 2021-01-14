import { Request, Response, NextFunction } from 'express';
import Publication from '../publication.model';
import User, { UserType } from '../../users/user.model';
import { UserAccountNotExist } from '../../../exceptions';
import { PdfParserHelper } from '../../../helpers';
import { promises as fs } from 'fs';

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
    const pdfData = await PdfParserHelper.getPdfData(req.file.path);
    publication.name = pdfData.name;
    publication.authors = pdfData.authors;
    publication.owners = [user]
    publication.file = `./${req.file.path}`;
    publication.doi = pdfData.doi    
    return res.json(publication);
}

export default addPublicationFromPdf;