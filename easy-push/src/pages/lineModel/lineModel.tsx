import { observer } from "mobx-react";

  
  const LineModel = (props:any) => {
    const { lines } = props

    //拼接日志内容
    const line: any[] = [];
    if (lines) {
      for (let i = 0; i < lines.length; i++) {
        console.log();
        line.push(
          <p key={"logline" + i} style={{ lineHeight: 1.8 }}>
            {" "}
            {lines[i]}{" "}
          </p>
        );
    }
    }
    return(
      <div>
        {line}
      </div>
    )
  };
  
  export default observer(LineModel);
  