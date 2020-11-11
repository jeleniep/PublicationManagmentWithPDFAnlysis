import HttpException from "./HttpException";

class UserAccountNotExist extends HttpException {
    constructor(email: string) {
        super(
            403,
            'account-not-exist',
            `User account with email ${email} not exist.`
        );
    }
}

export default UserAccountNotExist;
