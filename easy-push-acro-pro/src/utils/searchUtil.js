



//查询字段定义类型
export const SelectColumnType = {
    //指定字段 onlySelect=id,name
    onlySelect:'onlySelect',
    //排除字段 selectExclude=age
    selectExcludeL:'selectExclude'
}

//查询条件筛选方式
export const SearchType = {
//     （5）字段过滤（ [field]-op=eq ）#
// GET /user/index? age=20 & age-op=eq
EQ:'eq',

// （6）字段过滤（ [field]-op=ne ）#
// GET /user/index? age=20 & age-op=ne
// 返回结果：结构同 （1）（但只返回 age != 20 的数据，ne 是 NotEqual 的缩写）
NE:'ne',

// （7）字段过滤（ [field]-op=ge ）#
// GET /user/index? age=20 & age-op=ge
// 返回结果：结构同 （1）（但只返回 age >= 20 的数据，ge 是 GreateEqual 的缩写）
GE:'ge',

// （8）字段过滤（ [field]-op=le ）#
// GET /user/index? age=20 & age-op=le
// 返回结果：结构同 （1）（但只返回 age <= 20 的数据，le 是 LessEqual 的缩写）
LE:'le',

// （9）字段过滤（ [field]-op=gt ）#
// GET /user/index? age=20 & age-op=gt
// 返回结果：结构同 （1）（但只返回 age > 20 的数据，gt 是 GreateThan 的缩写）
GT:'gt',
// （10）字段过滤（ [field]-op=lt ）#
// GET /user/index? age=20 & age-op=lt
// 返回结果：结构同 （1）（但只返回 age < 20 的数据，lt 是 LessThan 的缩写）
LT:'lt',

// （11）字段过滤（ [field]-op=bt ）#
// GET /user/index? age-0=20 & age-1=30 & age-op=bt
// 返回结果：结构同 （1）（但只返回 20 <= age <= 30 的数据，bt 是 Between 的缩写）
BETWEEN:'bt',

// （12）字段过滤（ [field]-op=il ）#
// GET /user/index? age-0=20 & age-1=30 & age-2=40 & age-op=il
// 返回结果：结构同 （1）（但只返回 age in (20, 30, 40) 的数据，il 是 InList 的缩写）
IN:'il',

// （13）字段过滤（ [field]-op=ct ）#
// GET /user/index? name=Jack & name-op=ct
// 返回结果：结构同 （1）（但只返回 name 包含 Jack 的数据，ct 是 Contain 的缩写）
LIKE:'ct',

// NOT_IN='NOT_IN',

// （14）字段过滤（ [field]-op=sw ）#
// GET /user/index? name=Jack & name-op=sw
// 返回结果：结构同 （1）（但只返回 name 以 Jack 开头的数据，sw 是 StartWith 的缩写）
START_LIKE:'sw',
// （15）字段过滤（ [field]-op=ew ）#
// GET /user/index? name=Jack & name-op=ew
// 返回结果：结构同 （1）（但只返回 name 以 Jack 结尾的数据，sw 是 EndWith 的缩写）

END_LIKE:'ew',

// （16）字段过滤（ [field]-op=ey ）#
// GET /user/index? name-op=ey
// 返回结果：结构同 （1）（但只返回 name 为空 或为 null 的数据，ey 是 Empty 的缩写）
EMPTY:'ey',

// （17）字段过滤（ [field]-op=ny ）#
// GET /user/index? name-op=ny
// 返回结果：结构同 （1）（但只返回 name 非空 的数据，ny 是 NotEmpty 的缩写）
NOT_EMPTY:'ny',

// （18）忽略大小写（ [field]-ic=true ）#
// GET /user/index? name=Jack & name-ic=true
// 返回结果：结构同 （1）（但只返回 name 等于 Jack (忽略大小写) 的数据，ic 是 IgnoreCase 的缩写
IgnoreCase:'ic'

}

/*** 针对bean searcher进行查询条件转换条件 */
export function buildSearchCondition (columnsConfig,searchConfig,orders,queryParam){
    const searchParam = new Map();
    if(columnsConfig){
        if(columnsConfig.type == SelectColumnType.onlySelect){
            searchParam.set(SelectColumnType.onlySelect,columnsConfig.columns.join())
        }
        if(columnsConfig.type == SelectColumnType.selectExcludeL){
            searchParam.set(SelectColumnType.selectExcludeL,columnsConfig.columns.join())
        }
    }
   
    if(queryParam){
        for (const [key, value] of Object.entries(queryParam)) {
            if(value){
                console.log(value);
                searchParam.set(key,value);
                const op = searchConfig[key];
                if(op && op === SearchType.IgnoreCase){
                    console.log(op);
                    searchParam.set(key+'-ic',true);
                }
                if(op && op !== SearchType.IgnoreCase){
                    console.log(op);
                    searchParam.set(key+'-op',op);
                }
            }
          }
    }
    if(orders){
        // （3）数据排序（sort | order）# GET /user/index? sort=age & order=desc
        //{ column: sorter.field, asc: true }
        orders.forEach(sorter =>{
            console.log(sorter.column,sorter.asc);
            searchParam.set('sort',sorter.column);
            searchParam.set('order',sorter.asc? 'asc':'desc');
        });
    }
    return [...searchParam.entries()].reduce((obj, [key, value]) => (obj[key] = value, obj), {});
}