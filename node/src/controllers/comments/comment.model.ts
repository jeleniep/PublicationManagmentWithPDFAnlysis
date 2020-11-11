import mongoose, { Schema, Document } from 'mongoose';
import User, { UserType } from '../users/user.model';
import Publication, { PublicationType } from '../publications/publication.model';
import { IOwnership } from '../../interfaces';



export interface IComment {
    body: string,
    publication: PublicationType
}

export const CommentSchema = new Schema({
    body: { type: String, required: true },
    owners: {
        type: [{
            type: mongoose.Types.ObjectId,
             ref: User,
              required: true
        }],
        validate: [arrayLimit, 'Comment could have only one owner']

    },
    publication: { type: mongoose.Types.ObjectId, ref: Publication, required: true }
}, {
    timestamps: true
});

function arrayLimit(val: any) {
    return val.length === 1;
}


export type CommentType = IComment & Document & IOwnership


const Comment = mongoose.model<CommentType>('Comment', CommentSchema);
export default Comment;
