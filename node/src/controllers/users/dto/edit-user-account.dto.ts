import { IsString, ArrayMinSize, IsObject, ValidateNested, IsArray, IsOptional, IsEmail } from 'class-validator';
import { Type } from 'class-transformer';

class EditUserAccountDto {
    
    @IsOptional()
    @IsEmail()
    public readonly email: string;

    @IsOptional()
    @IsString()
    public readonly username: string;

    @IsString()
    public readonly currentPassword: string;

    @IsOptional()
    @IsString()
    public readonly newPassword: string;

    @IsOptional()
    @IsString()
    public readonly newPasswordRepeated: string;

}

export default EditUserAccountDto;
