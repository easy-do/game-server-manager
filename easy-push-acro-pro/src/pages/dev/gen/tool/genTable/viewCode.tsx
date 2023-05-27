import { preview } from '@/api/genTable';
import MEditor from '@/components/Medit/MEditor';
import useLocale from '@/utils/useLocale';
import { Tabs, Modal, Tree, Card } from '@arco-design/web-react';
import React, { useEffect, useState } from 'react';
import locale from './locale';
import Col from '@arco-design/web-react/es/Grid/col';
import Row from '@arco-design/web-react/es/Grid/row';


const TabPane = Tabs.TabPane;

function ViewCodePage({ id, visible, setVisible }) {
  const t = useLocale(locale);

  const [loading, setLoading] = useState(false);

  const [codeTabPanes, setCodeTabPanes] = useState([]);

  const [pathTreeData, setPathTreeData] = useState([]);

  const [expandedKeys, setExpandedKeys] = useState([]);

  const [currentSelectKey, setCurrentSelectKey] = useState<string>();


  //加载数据
  function fetchData() {
    if (id !== undefined && visible) {
      setLoading(true);
      preview(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          const list = [];
          const expandedKeyList: string[] = [];
          data.codes.forEach((element) => {
            list.push(
              <TabPane key={element.fileName} title={element.fileName}>
                <MEditor
                  key={element.fileName}
                  showLanguageSelect={true}
                  height={'600px'}
                  width={'1000px'}
                  theme="vs-dark"
                  language={element.templateType}
                  value={element.code}
                  callBack={null}
                />
              </TabPane>
            );
            expandedKeyList.push(element.fileName);
          });
          setPathTreeData(data.filePathTree);
          setCodeTabPanes(list);
          setCurrentSelectKey(expandedKeyList[0]);
          setExpandedKeys(expandedKeyList);
        }
        setLoading(false);
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [id, visible]);

  return (
    <Modal
      title={t['searchTable.view.title']}
      visible={visible}
      onOk={() => {
        setVisible(false);
      }}
      onCancel={() => {
        setVisible(false);
      }}
      autoFocus={false}
      focusLock={true}
      maskClosable={false}
      style={{ width: '90%', minHeight: '90%' }}
    >
      <div style={{ width: '100%' }}>
        <div style={{ width: '100%' }}>
          <Row>
            <Col span={8}>
              <Card key={'tree'} bordered style={{ height: '90%' }}>
                <Tree
                  onSelect={(selectedKeys: string[], _extra) => {
                    if(expandedKeys.indexOf(selectedKeys[0]) > -1){
                      setCurrentSelectKey(selectedKeys[0]);
                    }
                  }}
                  expandedKeys={expandedKeys}
                  onExpand={(keys, _extra) => {
                    setExpandedKeys(keys);
                  }}
                  size="small"
                  selectedKeys={[currentSelectKey]}
                  treeData={pathTreeData}
                />
              </Card>
            </Col>
            <Col span={16}>
              <Card key={'code'} bordered>
                <Tabs
                  activeTab={currentSelectKey}
                  onClickTab={(key: string) => {
                    setCurrentSelectKey(key);
                  }}
                  type="card-gutter"
                >
                  {codeTabPanes}
                </Tabs>
              </Card>
            </Col>
          </Row>
        </div>
      </div>
    </Modal>
  );
}

export default ViewCodePage;
