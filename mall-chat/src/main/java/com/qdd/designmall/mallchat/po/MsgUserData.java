package com.qdd.designmall.mallchat.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.common.serializer.PicUrlSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MsgUserData extends UserDto {
    String nickName;
    @JsonSerialize(using = PicUrlSerializer.class)
    String avatar;

    public MsgUserData(EUserType userType,Long userId,String nickName,String avatar){
        super(userType,userId);
        this.nickName = nickName;
        this.avatar = avatar;
    }
}
