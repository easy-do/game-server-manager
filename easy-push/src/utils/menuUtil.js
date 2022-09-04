
import { Nav } from "@douyinfe/semi-ui";
import { iocnMap } from "./iconUtil";


export const buildMenu = (item, level, navigate) => {
    if (item !== undefined && !item.disabled) {
      if (item.menuType === "M") {
        return (
          <Nav.Sub
            key={item.itemKey}
            itemKey={item.itemKey + ""}
            text={item.text}
            icon={iocnMap.get(item.icon)}
            level={level + 1}
            indent={true}
          >
            {item.items
              ? item.items.map((item1) => {
                  return buildMenu(item1, level + 1, navigate);
                })
              : null}
          </Nav.Sub>
        );
      } 
      if (item.menuType === "C") {
        return (
          <Nav.Item
            key={item.itemKey}
            itemKey={item.itemKey + ""}
            text={item.text}
            icon={iocnMap.get(item.icon)}
            level={level + 1}
            indent={true}
            onClick={()=>navigate(item.link)}
            linkOptions={{onClick:e=>e.preventDefault()}}
          />
        );
      }
    } else {
      return null;
    }
  };