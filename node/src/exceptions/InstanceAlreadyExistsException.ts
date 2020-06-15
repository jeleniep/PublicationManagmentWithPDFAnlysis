import HttpException from "./HttpException";

class InstanceAlreadyExistsException extends HttpException {
    constructor(name: string) {
        super(
            400,
            'already-exists',
            `Instance with name ${name} already exists`
        );
    }
}

export default InstanceAlreadyExistsException;
