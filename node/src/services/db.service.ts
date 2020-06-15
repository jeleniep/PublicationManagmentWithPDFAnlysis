import { createConnection, getConnection } from 'typeorm'

const getDbConnection = async () => (
    getConnection()
);

export default getDbConnection;