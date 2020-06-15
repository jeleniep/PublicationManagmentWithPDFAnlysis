import HttpException from "./HttpException";

class NotWhitelistedException extends HttpException {
    constructor() {
        super(
            403,
            'not-whitelisted',
            `The base instance you provided is not whitelisted`
        );
    }
}

export default NotWhitelistedException;
