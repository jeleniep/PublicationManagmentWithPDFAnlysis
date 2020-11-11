import { Router } from 'express';
import { UserType } from '../controllers/users/user.model';

interface IOwnership {
    owners: UserType[]
}

export default IOwnership;