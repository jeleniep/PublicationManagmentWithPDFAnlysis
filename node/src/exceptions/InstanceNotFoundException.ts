import HttpException from "./HttpException";

class InstanceNotFoundException extends HttpException {
    constructor(id: string) {
        super(
            404,
            'not-found',
            `Instance with id ${id} does not exist`
        );
    }
}

export default InstanceNotFoundException;
