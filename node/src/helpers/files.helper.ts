import { promises as fs } from 'fs';

export const moveFile = async (from: string, to: string) => {
    const splittedTo = to.split("/")

    let path = splittedTo
        .slice(0, splittedTo.length -1)
        .join('/').toLowerCase();
    console.log(from)
    console.log(to)
    console.log(path)
    await fs.mkdir(path, { recursive: true })

    await fs.copyFile(from, to)
    await fs.unlink(from);
};
