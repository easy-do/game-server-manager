import { Empty } from "@douyinfe/semi-ui";

export enum RoleEnum {
    SUPE_ADMIN = 'super_admin'
}


export enum SearchTypeEnum {
        EQ='eq',
        NE='ne',
        LT='lt',
        GT='gt',
        GE='ge',
        LE='ge',
        BETWEEN='between',
        NOT_BETWEEN='noBetween',
        LIKE='like',
        NOT_LIKE='like',
        LIKE_LEFT='likeLeft',
        LIKE_RIGHT='likeRight',
        NULL='null',
}

export class SearchParam {

    column :string;

    value:any|any[];

    searChType:SearchTypeEnum;

    constructor(column:string,value:any|any[],searChType:SearchTypeEnum){
        this.column=column
        this.value=value
        this.searChType=searChType
    }

  }
