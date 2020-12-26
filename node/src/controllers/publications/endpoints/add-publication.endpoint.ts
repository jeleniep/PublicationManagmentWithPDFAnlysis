import { Request, Response, NextFunction } from 'express';
import Publication from '../publication.model';
import User, { UserType } from '../../users/user.model';
import { UserAccountNotExist } from '../../../exceptions';
import { moveFile } from '../../../helpers'
import { PATH_TO_PDF } from '../../../config';


const addPublication = async (req: Request, res: Response, next: NextFunction) => {
    const { name, description, authors, tags, file, doi } = req.body;
    const { _id, email } = (<any>req).user;
    let user: UserType;
    try {
        user = await User.findById(_id).select({ email: 1, username: 1});
    } catch (error) {
        return next(new UserAccountNotExist(email));
    }
    const publication = new Publication();
    publication.name = name;
    publication.description = description;
    publication.tags = tags;
    publication.authors = authors;
    publication.doi = doi;
    console.log(doi, "Asdasdasd")
    publication.owners = [user]
    const splittedOldFilePath = file.split('/');
    const fileName = splittedOldFilePath[splittedOldFilePath.length - 1];
    const newPath = `${PATH_TO_PDF}/${_id}/${publication._id}/${fileName}`
    await moveFile(file, newPath);
    publication.file = newPath;
    await publication.save();
    res.json(publication);
}

export default addPublication;