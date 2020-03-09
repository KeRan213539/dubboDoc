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
      apiClassName: '',
      apiFunctionName: '',
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
      return;
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
          apiInfoData: apiInfoData,
          apiClassName: apiInfoData.apiModelClass,
          apiFunctionName: apiInfoData.apiName,
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
            key='formItemAsync'
            label='是否异步调用(此参数不可修改,根据接口定义的是否异步显示)'>
            <Select 
              id='formItemAsync'
              name='formItemAsync'
              style={{ marginLeft: 5, width: '100px' }}
              readOnly={true}
              defaultValue={this.state.apiInfoData.async}
            >
              <Select.Option value="true">true</Select.Option>
              <Select.Option value="false">false</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item
            key='apiClassName'
            label='接口模块(此参数不可修改)'>
            <Input
              id='apiClassName'
              htmlType={'text'}
              name='apiClassName'
              style={{ marginLeft: 5, width: 400 }}
              defaultValue={this.state.apiClassName}
              readOnly={true}
            />
          </Form.Item>
          <Form.Item
            key='apiFunctionName'
            label='接口方法名(此参数不可修改)'>
            <Input
              id='apiFunctionName'
              htmlType={'text'}
              name='apiFunctionName'
              style={{ marginLeft: 5, width: 400 }}
              defaultValue={this.state.apiFunctionName}
              readOnly={true}
            />
          </Form.Item>
          <Form.Item
            key='registryCenterUrl'
            label='注册中心地址, 如果为空将使用Dubbo 提供者Ip和端口进行直连'>
            <Input
              id='registryCenterUrl'
              htmlType={'text'}
              name='registryCenterUrl'
              style={{ marginLeft: 5, width: 400 }}
              placeholder='nacos://127.0.0.1:8848'
              trim={true}
            />
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
            onClick={ this.doTestApi.bind(this) }
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
    var allFromItems = $('.dubbo-doc-form-item-class input, .dubbo-doc-form-item-class  select, .dubbo-doc-form-item-class textarea');
    var tempMap = new Map();
    allFromItems.each((index, element) => {
      var elementIdSplited = element.id.split('@@');
      var tempMapKey = elementIdSplited[0];
      var tempMapValueArray = tempMap.get(tempMapKey);
      if(!tempMapValueArray){
        tempMapValueArray = new Array();
        tempMap.set(tempMapKey + "@@" + elementIdSplited[1], tempMapValueArray);
      }
      tempMapValueArray.push(element);
    });
    var postData = new Array();
    tempMap.forEach((value, key) => {
      var postDataItem = {};
      postData.push(postDataItem);
      postDataItem['prarmType'] = key.split('@@')[0];
      var postDataItemValue = {};
      postDataItem['prarmValue'] = postDataItemValue;
      value.forEach(element => {
        if(element.tagName == 'TEXTAREA'){
          if(element.value != ''){
            postDataItemValue[element.name] = JSON.parse(element.value);
          }
        } else {
          var elementValue = element.value;
          if($(element).attr('aria-valuetext')){
            elementValue = $(element).attr('aria-valuetext');
          }
          postDataItemValue[element.name] = elementValue;
        }
      });
    });
    var registryCenterUrl = $('#registryCenterUrl').val();
    if(registryCenterUrl == ''){
      registryCenterUrl = 'dubbo://' + this.state.dubboIp + ':' + this.state.dubboPort;
    }
    console.log(postData);
    console.log(JSON.stringify(postData));
    request({
      url: '/api/requestDubbo',
      method: 'post',
      params: {
        async: $('#formItemAsync').attr('aria-valuetext'),
        interfaceClassName: $('#apiClassName').val(),
        methodName: $('#apiFunctionName').val(),
        registryCenterUrl: registryCenterUrl
      },
      headers: {
        'Content-Type': 'application/json; charset=UTF-8' 
      },
      data: JSON.stringify(postData),
    }).catch(error => {
      console.log(error);
    }).then(response => {
      console.log(response);
    });
  }

  showApiInfo(){
    return (
      <div>
        <h1>接口名称: <span>{this.state.apiInfoData.apiChName + '(' + this.state.apiName + ')'}</span></h1>
        <h1>接口说明: <span>{this.state.apiInfoData.apiRespDec}</span></h1>
        <h1>接口版本: <span>{this.state.apiInfoData.apiVersion}</span></h1>
      </div>
    );
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

  buildInputChar(item){
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
        maxLength={1}
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
        style={{ marginLeft: 5, width: '400px' }}
        dataSource={dataSource}
      />
    );
  }

  buildFormItem(item){
    switch (item.get('htmlType')) {
      case 'TEXT':
        return this.buildInputText(item);
        break;
      case 'TEXT_BYTE':
        return this.buildInputText(item);
        break;
      case 'TEXT_CHAR':
        return this.buildInputChar(item);
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
          {this.showApiInfo()}
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