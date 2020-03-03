import Home from '@/pages/Home';
import ApiForm from '@/pages/ApiForm';

const routes = [{
  path: '/apiForm',
  component: ApiForm,
}, {
  path: '/',
  component: Home,
}];

export default routes;