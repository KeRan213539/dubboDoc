import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { AppLink } from '@ice/stark';
import { Nav } from '@alifd/next';
import { store as appStore } from 'ice'

const SubNav = Nav.SubNav;
const NavItem = Nav.Item;

export interface IMenuItem {
  name: string;
  path: string;
  icon?: string;
  children?: IMenuItem[];
}

function getNavMenuItems(menusData: any[], isCollapse: boolean) {
  if (!menusData) {
    return [];
  }

  return menusData
    .filter(item => item.name && !item.hideInMenu)
    .map((item, index) => {
      return getSubMenuOrItem(item, index, isCollapse);
    });
}

function getSubMenuOrItem(item: IMenuItem, index: number, isCollapse: boolean) {
  if (item.children && item.children.some(child => child.name)) {
    const childrenItems = getNavMenuItems(item.children, false);
    if (childrenItems && childrenItems.length > 0) {
      const subNav = (
        <SubNav
          key={index}
          icon={item.icon}
          label={item.name}
          mode={isCollapse ? 'popup' : 'inline'}
        >
          {childrenItems}
        </SubNav>
      );

      return subNav;
    }
    return null;
  }
  const navItem = (
    <NavItem key={item.path} icon={item.icon}>
      <AppLink to={item.path}>
        {item.name}
      </AppLink>
    </NavItem>
  );

  return navItem;
}

const Navigation = (props, context) => {
  const { pathname } = props;
  const { isCollapse } = context;
  const [ menuState, menuAction ] = appStore.useModel('asideMenu');

  const [asideMenus, setAsideMenus] = useState(menuState.asideMenus);

  return (
    <Nav
      type="primary"
      selectedKeys={[pathname]}
      defaultSelectedKeys={[pathname]}
      embeddable
      openMode="single"
      iconOnly={isCollapse}
      hasArrow={false}
    >
      {getNavMenuItems(asideMenus, isCollapse)}
    </Nav>
  );
};

Navigation.contextTypes = {
  isCollapse: PropTypes.bool,
};

export default Navigation;
