import React from 'react';
import { request } from 'ice';
import {
  Message,
  Dialog,
  NumberPicker,
  Form,
  Input,
  Loading,
  Select,
} from '@alifd/next';
import $ from 'jquery'
import ReactJson from 'react-json-view'

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
      buildResponseInfo: false,  // 是否渲染返回描述
      responseInfo: '',
      buildResponseData: false,  // 是否渲染接口返回内容
      responseData: '',
    };

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
      Message.error('获取接口信息异常');
      console.log(error);
    }).then(response => {
      if(response && response != ''){
        const apiInfoData = JSON.parse(response);
        this.setState({
          loading: false,
          apiInfoData: apiInfoData,
          apiClassName: apiInfoData.apiModelClass,
          apiFunctionName: apiInfoData.apiName,
          buildResponseInfo: true,
          responseInfo: apiInfoData.response
        });
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
          formItem.set('required', paramItem.required);
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
            formItem.set('required', paramItem.required);
            formsArray.push(formItem);
          }
        }
      }
      return(
        <div>
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
                  <div key={'formDiv' + index} style={{ marginTop: 20 }}>
                    {'参数名: ' + item.get('name')}
                    <div style={{ width: '1000px', height:'220px' }}>
                      <div style={{ float: 'left', border: '2px solid #cccccc', 
                            width: '300px', height: '100%', padding: '5px' }}>
                        Description:<br />
                        {item.get('description')}
                      </div>
                      <div style={{float: "left"}}>
                        <Form.Item 
                          help={item.get('required') ? '该参数为必填' : ''}
                          key={'formItem' + index}
                          required={item.get('required')}
                          style={{padding: '5px'}}
                          label={item.get('nameCh')}
                        >
                        {
                          this.buildFormItem(item)
                        }
                        </Form.Item>
                      </div>
                    </div>
                  </div>
                )
              })
            }
            <Form.Submit
              type={'primary'}
              validate
              style={{ marginLeft: 20, marginTop: 20, width: '600px' }}
              onClick={ this.doTestApi.bind(this) }
            >
              测试
            </Form.Submit>
          </Form>
          <div style={{marginTop: 30, backgroundColor: '#3e3e3e', color: '#f0f8ff'}}>
            <h1>响应</h1>
          </div>
          <div>
            <div
              style={{float: 'left'}}
            >
              <div
                style={{backgroundColor: '#3971a2', color: '#f0f8ff'}}
              >
                <h3>响应示例</h3>
              </div>
            {this.buildResponseInfoView()}
            </div>
            <div
              style={{float: 'left', marginLeft: 50}}
            >
              <div
                style={{backgroundColor: '#3c9069', color: '#f0f8ff'}}
              >
                <h3>接口响应</h3>
              </div>
            {this.buildResponseDataView()}
            </div>
          </div>
        </div>
        
      );
    } else {
      return (
        <h1>加载中...</h1>
      );
    }
    
  }

  buildResponseInfoView(){
    if(!this.state.buildResponseInfo){
      return (
        <Input.TextArea
          readOnly
          style={{ marginLeft: 5, width: '600px' }}
          rows={10}
        />
      );
    }
    try{
      var responseInfoJsonObj = JSON.parse(this.state.responseInfo);
      if(typeof(responseInfoJsonObj) == 'string'){
        throw 'data not json';
      }
      return (
        <ReactJson 
          name={false} 
          src={responseInfoJsonObj} 
        />
      );
    } catch (e) {
      return (
        <Input.TextArea
          readOnly
          style={{ marginLeft: 5, width: '600px' }}
          rows={10}
          defaultValue={this.state.responseInfo}
        />
      );
    }
  }

  buildResponseDataView(){
    if(!this.state.buildResponseData){
      return (
        <Input.TextArea
          readOnly
          style={{ marginLeft: 5, width: '600px' }}
          rows={10}
        />
      );
    }
    try{
      if(typeof(this.state.responseData) == 'string'){
        throw 'data not json';
      }
      return (
        <ReactJson 
          style={{ marginLeft: 5}}
          name={false} 
          src={this.state.responseData} 
        />
      );
    } catch (e) {
      return (
        <Input.TextArea
          style={{ marginLeft: 5, width: '600px' }}
          rows={10}
          readOnly
          value={this.state.responseData}
        />
      );
    }
  }

  doTestApi(v, e){
    if(e){
      Message.error('有未填写的必填项');
      return false;
    }
    var allFromItems = $('.dubbo-doc-form-item-class input, .dubbo-doc-form-item-class  select, .dubbo-doc-form-item-class textarea');
    var tempMap = new Map();
    allFromItems.each((index, element) => {
      // 把所有表单元素按照dubbo接口参数Bean分类
      var elementIdSplited = element.id.split('@@');
      var tempMapKey = elementIdSplited[0] + "@@" + elementIdSplited[1];
      var tempMapValueArray = tempMap.get(tempMapKey);
      if(!tempMapValueArray){
        tempMapValueArray = new Array();
        tempMap.set(tempMapKey, tempMapValueArray);
      }
      tempMapValueArray.push(element);
    });
    var postData = [];
    tempMap.forEach((value, key) => {
      var postDataItem = {};
      postData[key.split('@@')[1]] = postDataItem;
      postDataItem['prarmType'] = key.split('@@')[0];
      var postDataItemValue = {};
      postDataItem['prarmValue'] = postDataItemValue;
      value.forEach(element => {
        var elementName = element.name.split('@@')[3];
        if(element.tagName == 'TEXTAREA'){
          if(element.value != ''){
            postDataItemValue[elementName] = JSON.parse(element.value);
          }
        } else {
          var elementValue = element.value;
          if($(element).attr('aria-valuetext')){
            elementValue = $(element).attr('aria-valuetext');
          }
          postDataItemValue[elementName] = elementValue;
        }
      });
    });
    var registryCenterUrl = $('#registryCenterUrl').val();
    if(registryCenterUrl == ''){
      registryCenterUrl = 'dubbo://' + this.state.dubboIp + ':' + this.state.dubboPort;
    }
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
      Message.error('请求接口发生异常,请检查提交的数据,特别是JSON类数据和其中的枚举部分');
      console.log(error);
    }).then(response => {
      this.setState({
        buildResponseData: true,
        responseData: response
      });
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
        id={item.get('paramType') + '@@' + item.get('paramIndex') + '@@' + item.get('javaType') + "@@" + item.get('name')}
        htmlType={'text'}
        name={item.get('paramType') + '@@' + item.get('paramIndex') + '@@' + item.get('javaType') + "@@" + item.get('name')}
        className={'dubbo-doc-form-item-class'}
        style={{ marginLeft: 5, width: 400 }}
        placeholder={item.get('example')}
        defaultValue={item.get('defaultValue')}
        trim={true}
        aria-required={item.get('required')}
      />
    );
  }

  buildInputChar(item){
    return (
      <Input
        htmlType={'text'}
        name={item.get('paramType') + '@@' + item.get('paramIndex') + '@@' + item.get('javaType') + "@@" + item.get('name')}
        className={'dubbo-doc-form-item-class'}
        style={{ marginLeft: 5, width: 400 }}
        placeholder={item.get('example')}
        defaultValue={item.get('defaultValue')}
        trim={true}
        maxLength={1}
        aria-required={item.get('required')}
      />
    );
  }

  buildNumberInteger(item){
    return (
      <NumberPicker
        name={item.get('paramType') + '@@' + item.get('paramIndex') + '@@' + item.get('javaType') + "@@" + item.get('name')}
        className={'dubbo-doc-form-item-class'}
        style={{ marginLeft: 5, width: 400 }}
        placeholder={item.get('example')}
        defaultValue={item.get('defaultValue') - 0}
        type='inline'
        aria-required={item.get('required')}
      />
    );
  }

  buildNumberDecimal(item){
    return (
      <NumberPicker
        name={item.get('paramType') + '@@' + item.get('paramIndex') + '@@' + item.get('javaType') + "@@" + item.get('name')}
        className={'dubbo-doc-form-item-class'}
        style={{ marginLeft: 5, width: 400 }}
        placeholder={item.get('example')}
        defaultValue={item.get('defaultValue') - 0}
        type='inline'
        precision={20}
        aria-required={item.get('required')}
      />
    );
  }

  buildReactJson(item){
    return (
      <ReactJson 
        onEdit={o => {
          console.log(o);
        }}
        onAdd={o => {
          console.log(o);
        }}
        onDelete={o => {
          console.log(o);
        }} 
        name={false} 
        src={[{'abc': 123, 'cde': 'wdsfasdf'},{'abc': 123, 'cde': 'wdsfasdf'}]} 
      />
    )
  }

  buildTestArea(item){
    return (
      <Input.TextArea
        name={item.get('paramType') + '@@' + item.get('paramIndex') + '@@' + item.get('javaType') + "@@" + item.get('name')}
        className={'dubbo-doc-form-item-class'}
        style={{ marginLeft: 5, width: '600px' }}
        rows={10}
        placeholder={item.get('example')}
        defaultValue={JSON.stringify(JSON.parse(item.get('subParamsJson')), null, 4)}
        trim={true}
        aria-required={item.get('required')}
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
        name={item.get('paramType') + '@@' + item.get('paramIndex') + '@@' + item.get('javaType') + "@@" + item.get('name')}
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