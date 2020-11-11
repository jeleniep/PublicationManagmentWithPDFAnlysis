import HttpException from "./HttpException";

class NotEnoughPermissionsException extends HttpException {
    constructor() {
        super(
            403,
            'not-enough-permissions',
            `You don't have enough permissions to perform that action.`
        );
    }
}

export default NotEnoughPermissionsException;
