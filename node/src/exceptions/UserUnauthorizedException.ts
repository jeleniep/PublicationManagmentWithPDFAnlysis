import HttpException from "./HttpException";

class UserUnauthorizedException extends HttpException {
    constructor() {
        super(
            401,
            'user-unauthorized',
            'User is not authorized'
        );
    }
}

export default UserUnauthorizedException;
