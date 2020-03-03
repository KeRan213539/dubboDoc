const asideMenu = {
    state: {
        asideMenus: new Array(),
    },
    actions: {
      loadMenus(prevState, apiArray, actions) {
        let _thisAsideMenus = asideMenu.state.asideMenus;
        _thisAsideMenus.length = 0;
        for(let i = 0; i < apiArray.length; i++){
          let item = apiArray[i];
          _thisAsideMenus.push(item);
        }
        return _thisAsideMenus;
      },
    },
  };
  
  export default asideMenu;