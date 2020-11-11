import mongoose, { Schema, Document } from 'mongoose';
import User, { IUser } from './user.model';



export interface IUserForgottenPassword {
    user: IUser,
    token: string,
    createdAt: Date
}

export const UserForgottenPasswordSchema = new Schema({
    user: { type: mongoose.Types.ObjectId, ref: User, required: true, unique: true, index: true },
    token: { type: String, required: true, unique: true, index: true },
    createdAt: { type: Date, expires: 600, default: Date.now }
}, {
    timestamps: true
});

// UserForgottenPasswordSchema.index({ user: 1, token: 1 }, { unique: true });
// export {UserForgottenPasswordSchema};

export type UserForgottenPasswordType = IUserForgottenPassword & Document

const UserForgottenPassword = mongoose.model<UserForgottenPasswordType>('UserForgottenPassword', UserForgottenPasswordSchema);
export default UserForgottenPassword;
