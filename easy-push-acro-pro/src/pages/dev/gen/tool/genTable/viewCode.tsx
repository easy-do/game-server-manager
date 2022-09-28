import { preview } from '@/api/genTable';
import MEditor from '@/components/Medit/MEditor';
import useLocale from '@/utils/useLocale';
import { Tabs, Modal, Spin } from '@arco-design/web-react';
import React, { useEffect, useState } from 'react';
import locale from './locale';

const TabPane = Tabs.TabPane;


function ViewCodePage({ id, visible, setVisible }) {
  const t = useLocale(locale);

  const [loading, setLoading] = useState(false);

  const [codeTabPanes, setCodeTabPanes] = useState([]);

  //加载数据
  function fetchData() {
    if (id !== undefined && visible) {
      setLoading(true);
      preview(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          const list = [];
          const map = new Map(Object.entries(data));
          map.forEach((value:string, key: string) => {
            list.push(
              <TabPane key={key} title={key}>
                <MEditor showLanguageSelect={true} height={'600px'} width={'950px'} theme='vs-dark' language='typescript' value={value} callBack={null} />
              </TabPane>
            );
          });
          console.info(list[0]);
          console.info(list[0].key);
          setCodeTabPanes(list);
        }
        setLoading(false);
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [id,visible]);

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
      style={{ width: '1000px', height: '800px' }}
    >
        <Spin tip="loading Data..." loading={loading}>
          <Tabs style={{ width: '950px', height: '650px' }}>{codeTabPanes}</Tabs>
        </Spin>
    </Modal>
  );
}

export default ViewCodePage;
