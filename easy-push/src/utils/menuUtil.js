
import { Nav } from "@douyinfe/semi-ui";
import { iocnMap } from "./iconUtil";


export const buildMenu = (item, level, push) => {
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
            {item.children
              ? item.children.map((item1) => {
                  return buildMenu(item1, level + 1, push);
                })
              : null}
          </Nav.Sub>
        );
      } 
      if (item.menuType === "C" && item.isFrame === 1) {
        return (
          <Nav.Item
            key={item.itemKey}
            itemKey={item.link}
            text={item.text}
            icon={iocnMap.get(item.icon)}
            level={level + 1}
            indent={true}
            onClick={({ itemKey, domEvent }) => push(domEvent, String(itemKey))}
          />
        );
      }

      if (item.menuType === "C" && item.isFrame === 0) {
        return (
          <Nav.Item
            key={item.itemKey}
            itemKey={item.link}
            text={item.text}
            icon={iocnMap.get(item.icon)}
            level={level + 1}
            indent={true}
            link={item.link}
          />
        );
      }
    } else {
      return null;
    }
  };