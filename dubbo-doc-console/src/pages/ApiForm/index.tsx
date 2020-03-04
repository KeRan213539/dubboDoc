import React from 'react';
import { request } from 'ice';
import {
  Button,
  Dialog,
  NumberPicker,
  Form,
  Input,
  Loading,
  Select,
} from '@alifd/next';
import $ from 'jquery'
import { Item } from '@alifd/next/types/timeline';

class ApiForm extends React.Component {

  constructor(props) {
    super(props);

    var params = new URLSearchParams(this.props.location.search);
    this.state = {
      loading: true,
      apiName: params.get('apiName'),
      dubboIp: params.get('dubboIp'),
      dubboPort: params.get('dubboPort'),
      apiInfoData:{},
    }

    let apiName = this.state.apiName;
    let dubboIp = this.state.dubboIp;
    let dubboPort = this.state.dubboPort;
    if(!apiName || apiName == '' || !dubboIp || dubboIp == '' || !dubboPort || dubboPort == ''){
      Dialog.alert({
        title: '缺少接口信息',
        style: {
          width: '60%',
        },
        content: (
            <div style={{ fontSize: '15px', lineHeight: '22px' }}>
              缺少接口信息
            </div>
        ),
      });
    }

    request({
      url: '/api/apiParamsResp',
      method: 'get',
      params: {
        dubboIp: this.state.dubboIp,
        dubboPort: this.state.dubboPort,
        apiName: this.state.apiName
      },
    }).catch(error => {
      console.log(error);
    }).then(response => {
      if(response && response != ''){
        const apiInfoData = JSON.parse(response);
        this.setState({
          loading: false,
          apiInfoData: apiInfoData
        })
      } else {
        Dialog.alert({
          title: '接口名称不正确,没有查找到接口参数和响应信息',
          style: {
            width: '60%',
          },
          content: (
              <div style={{ fontSize: '15px', lineHeight: '22px' }}>
                接口名称不正确,没有查找到接口参数和响应信息
              </div>
          ),
        });
      }
    });
  }

  showApiName(){
    return (
      <span>{this.state.apiName}</span>
    );
  }

  loadApiInfoAndBuildForm(){
    if(!this.state.loading){
      var apiInfoData = this.state.apiInfoData;
      var params = apiInfoData.params;
      var formsArray = new Array();
      for(var i = 0; i < params.length; i++){
        var paramItem = params[i];
        if(paramItem.htmlType){
          // 有 htmlType ,说明是个基础类型
          var formItem = new Map();
          formItem.set('name', paramItem.name);
          formItem.set('htmlType', paramItem.htmlType);
          formItem.set('paramType', paramItem.prarmType);
          formItem.set('javaType', paramItem.prarmType);
          formItem.set('paramIndex', paramItem.prarmIndex);
          formItem.set('nameCh', paramItem.nameCh);
          formItem.set('description', paramItem.description);
          formItem.set('example', paramItem.example);
          formItem.set('defaultValue', paramItem.defaultValue);
          formItem.set('allowableValues', paramItem.allowableValues);
          formsArray.push(formItem);
        } else {
          // 没有 htmlType, 说明是个对象
          var prarmInfoArray = paramItem.prarmInfo;
          for(var j = 0; j < prarmInfoArray.length; j++){
            var prarmInfoItem = prarmInfoArray[j];
            var formItem = new Map();
            formItem.set('name', prarmInfoItem.name);
            formItem.set('htmlType', prarmInfoItem.htmlType);
            formItem.set('paramType', paramItem.prarmType);
            formItem.set('javaType', prarmInfoItem.javaType);
            formItem.set('paramIndex', paramItem.prarmIndex);
            formItem.set('nameCh', prarmInfoItem.nameCh);
            formItem.set('description', prarmInfoItem.description);
            formItem.set('example', prarmInfoItem.example);
            formItem.set('defaultValue', prarmInfoItem.defaultValue);
            formItem.set('allowableValues', prarmInfoItem.allowableValues);
            formItem.set('subParamsJson', prarmInfoItem.subParamsJson);
            formsArray.push(formItem);
          }
        }
      }
      return(
        <Form>
          <Form.Item
            key=''
            label=''>
              
            </Form.Item>
          {
            formsArray.map((item, index) => {
              return (
                <Form.Item
                key={'formItem' + index}
                label={'Name: ' + item.get('name')}>
                  <div style={{ width: '1000px', height:'200px' }}>
                    <div style={{ float: 'left', border: '2px solid #cccccc', 
                          width: '300px', height: '100%', padding: '5px' }}>
                      Description:<br />
                      {item.get('description')}
                    </div>
                    <div style={{float: "left"}}>
                      <div style={{padding: '5px'}}>{item.get('nameCh')}</div>
                      <div>
                      {
                        this.buildFormItem(item)
                      }
                      </div>
                    </div>
                  </div>
                </Form.Item>
              )
            })
          }
          <Button
            type={'primary'}
            style={{ marginLeft: 10, width: '600px' }}
            onClick={ this.doTestApi }
          >
            测试
          </Button>
        </Form>
      );
    } else {
      return (
        <h1>加载中...</h1>
      );
    }
    
  }

  doTestApi(){
    var testInputs = $('.dubbo-doc-form-item-class input');
    testInputs.each((index, element) => {
      console.log(element.id);
    });
  }

  buildInputText(item){
    return (
      <Input
        id={item.get('paramType') + '@@' + item.get('paramIndex') + '@@' + item.get('javaType')}
        htmlType={'text'}
        name={item.get('name')}
        className={'dubbo-doc-form-item-class'}
        style={{ marginLeft: 5, width: 400 }}
        placeholder={item.get('example')}
        defaultValue={item.get('defaultValue')}
        trim={true}
      />
    );
  }

  buildNumberInteger(item){
    return (
      <NumberPicker
        id={item.get('paramType') + '@@' + item.get('paramIndex') + '@@' + item.get('javaType')}
        name={item.get('name')}
        className={'dubbo-doc-form-item-class'}
        style={{ marginLeft: 5, width: 400 }}
        placeholder={item.get('example')}
        defaultValue={item.get('defaultValue') - 0}
        type='inline'
      />
    );
  }

  buildNumberDecimal(item){
    return (
      <NumberPicker
        id={item.get('paramType') + '@@' + item.get('paramIndex') + '@@' + item.get('javaType')}
        name={item.get('name')}
        className={'dubbo-doc-form-item-class'}
        style={{ marginLeft: 5, width: 400 }}
        placeholder={item.get('example')}
        defaultValue={item.get('defaultValue') - 0}
        type='inline'
        precision={20}
      />
    );
  }

  buildTestArea(item){
    return (
      <Input.TextArea
        id={item.get('paramType') + '@@' + item.get('paramIndex') + '@@' + item.get('javaType')}
        name={item.get('name')}
        className={'dubbo-doc-form-item-class'}
        style={{ marginLeft: 5, width: '600px' }}
        placeholder={item.get('example')}
        defaultValue={JSON.stringify(JSON.parse(item.get('subParamsJson')), null, 4)}
        trim={true}
        rows={10}
      />
    );
  }

  buildSelect(item){
    var allowableValues = item.get('allowableValues');
    const dataSource = new Array();
    for(var i = 0; i < allowableValues.length; i++){
      var valueItem = allowableValues[i];
      var dsItem = {};
      dsItem.label=valueItem;
      dsItem.value=valueItem;
      dataSource.push(dsItem);
    }
    return (
      <Select 
        id={item.get('paramType') + '@@' + item.get('paramIndex') + '@@' + item.get('javaType')}
        name={item.get('name')}
        className={'dubbo-doc-form-item-class'}
        style={{ marginLeft: 5, width: '200px' }}
        dataSource={dataSource}
      />
    );
  }

  buildFormItem(item){
    // 把dubbo的 接口类名和方法名拆出来,放到 state里面
    // TODO 加几个不可编辑并有默认值的input,到时候直接取: 接口类名, 接口方法名, 是否异步(从接口返回中取)
    // 加个注册中心地址的input,默认空:  注册中心地址,如: nacos://127.0.0.1:8848, 如果为空将使用Dubbo 提供者Ip和端口进行直连
    // 考虑把什么东西放到Input 的id里, 其他的属性参数重设(从item里面取)
    // 从表单取东西来拼参数
    switch (item.get('htmlType')) {
      case 'TEXT':
        return this.buildInputText(item);
        break;
      case 'TEXT_BYTE':
        return this.buildInputText(item);
        break;
      case 'TEXT_CHAR':
        return this.buildInputText(item);
        break;
      case 'NUMBER_INTEGER':
        return this.buildNumberInteger(item);
        break;  
      case 'NUMBER_DECIMAL':
        return this.buildNumberDecimal(item);
        break;
      case 'SELECT':
        return this.buildSelect(item);
        break;
      case 'TEXT_AREA':
        return this.buildTestArea(item);
        break;
      default:
        return (<span>未知类型</span>);
        break;
    }
  }


  render() {

    return (
      <div>
        <div>
          <h1>接口名称: {this.showApiName()}</h1>
        </div>
        <Loading
          shape={'flower'}
          style={{ position: 'relative', width: '100%', overflow: 'auto' }}
          visible={this.state.loading}
          tip={'Loading...'}
          color={'#333'}
        >
          <div>
            {this.loadApiInfoAndBuildForm()}
          </div>
        </Loading>
      </div>
     
    );
  }
};

export default ApiForm;