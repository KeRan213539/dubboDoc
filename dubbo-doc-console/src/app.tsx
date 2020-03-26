import * as React from 'react';
import { createApp } from 'ice'
import { ConfigProvider } from '@alifd/next';
import NotFound from '@/components/NotFound';
import PageLoading from '@/components/PageLoading';
import BasicLayout from '@/layouts/BasicLayout';

const appConfig = {
  app: {
    rootId: 'icestark-container',
    addProvider: ({ children }) => (
      <ConfigProvider prefix="next-icestark-">{children}</ConfigProvider>
    ),
  },
  router: {
    type: 'hash',
  },
  icestark: {
    type: 'framework',
    Layout: BasicLayout,
    getApps: async () => {
      const apps = [
        // {
        // path: '/seller',
        // title: '商家平台',
        // url: [
        //   '//ice.alicdn.com/icestark/child-seller-react/index.js',
        //   '//ice.alicdn.com/icestark/child-seller-react/index.css',
        // ],
        // }, {
        //   path: '/waiter',
        //   title: '小二平台',
        //   url: [
        //     '//ice.alicdn.com/icestark/child-waiter-vue/app.js',
        //     '//ice.alicdn.com/icestark/child-waiter-vue/app.css'
        //   ],
        // }, {
        //   // path: '/test',
        //   // title: 'test',
        //   // url: [
        //   //   'http://localhost:3334/js/index.js',
        //   //   'http://localhost:3334/css/index.css'
        //   // ]
        // }
      ];
      return apps;
    },
    appRouter: {
      NotFoundComponent: NotFound,
      LoadingComponent: PageLoading,
    },
  },
};

createApp(appConfig)
