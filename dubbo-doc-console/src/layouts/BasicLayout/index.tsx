import React, { useState, useEffect } from 'react';
import { Shell, Input, Button } from '@alifd/next';
import PageNav from './components/PageNav';
import Footer from './components/Footer';
import { request } from 'ice';

export default function BasicLayout(props: {
  children: React.ReactNode;
  pathname: string;
}) {
  const { children, pathname } = props;


  const [dubboIp, setDubboIp] = useState('127.0.0.1');

  const [dubboPort, setDubboPort] = useState('20881');

  const [menuData4Nav, setData4Nav] = useState(new Array());



  async function loadMenus(){
    const response = await request({
      url: '/api/apiModuleList',
      method: 'get',
      params: {
        dubboIp: dubboIp,
        dubboPort: dubboPort
      },
    }).catch(error => {
      console.log(error);
    });
    let resultData = new Array();
    if(response && response != ''){
      const menuData = JSON.parse(response);
      menuData.sort((a,b) => {
        return a.moduleChName > b.moduleChName;
      });
      for(let i = 0; i < menuData.length; i++){
        const menu = menuData[i];
        menu.moduleApiList.sort((a,b) => {
          return a.apiName > b.apiName;
        });
        const menu2 = {
          name: menu.moduleChName,
          path: '',
          icon: 'cascades',
          children: new Array(),
        };
        const menuItems = menu.moduleApiList;
        for(let j = 0; j < menuItems.length; j++){
          const menuItem = menuItems[j];
          const menuItem2 = {
            name: menuItem.apiChName,
            path: `/apiForm?apiName=${menu.moduleClassName}.${menuItem.apiName}&dubboIp=${dubboIp}&dubboPort=${dubboPort}`,
          };
          menu2.children.push(menuItem2);
        }
        resultData.push(menu2);
      }
    }
    setData4Nav(resultData);
  }

  return (
    <Shell
      type="dark"
      style={{
        minHeight: '100vh',
      }}
    >
      <Shell.Branding>
        <div style={{float: 'left', width: '200px'}}>Dubbo Doc</div>
        <div style={{float: 'right'}}>
          <span>Dubbo 提供者Ip:</span>
          <Input
            htmlType={'text'}
            style={{ marginLeft: 5, width: 200 }}
            placeholder={'127.0.0.1'}
            value={dubboIp}
            onChange={setDubboIp}
          />
          <span style={{ marginLeft: 10 }}>Dubbo 提供者端口:</span>
          <Input
            htmlType={'number'}
            style={{ marginLeft: 5, width: 80 }}
            placeholder={'20880'}
            value={dubboPort}
            onChange={setDubboPort}
          />
          <Button
            type={'primary'}
            style={{ marginLeft: 10 }}
            onClick={ loadMenus }
          >
            加载接口列表
          </Button>
        </div>
      </Shell.Branding>

      <Shell.Navigation>
        <PageNav pathname={pathname} menuData={menuData4Nav} />
      </Shell.Navigation>

      <Shell.Content>{children}</Shell.Content>
      <Shell.Footer>
        <Footer />
      </Shell.Footer>
    </Shell>
  );
}
