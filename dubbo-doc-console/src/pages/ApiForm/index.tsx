import React from 'react';
import { request } from 'ice';
import {
  Button,
  Dialog,
  Field,
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

  test(){
    var testInputs = $('.testInputClass input');
    testInputs.each((index, element) => {
      console.log(element.id);
    });
    
  }

  buildInputText(){
    return (
      <Input
        id={'textId1'}
        htmlType={'text'}
        className="testInputClass"
        style={{ marginLeft: 5, width: 200 }}
        placeholder={'xxxxx'}
        data-test="123"
      />
    );
  }

  buildFormItem(itemType){
    switch (itemType) {
      case 'text':
        return this.buildInputText();
        break;
      default:
        return this.buildInputText();
        break;
    }
  }

  loadApiInfoAndBuildForm(){
    if(!this.state.loading){
      var objArray = new Array();
      var item1 = "text";
      objArray.push(item1);
      var item2 = "text";
      objArray.push(item2);


      var apiInfoData = this.state.apiInfoData;
      var params = apiInfoData.params;
      var formsArray = new Array();
      for(var i = 0; i < params.length; i++){
        var paramItem = params[i];
        var formItem = new Map();
        if(paramItem.htmlType){
          // 有 htmlType ,说明是个基础类型
          formItem.set("htmlType", paramItem.htmlType);
          formItem.set("paramType", paramItem.prarmType);
          formItem.set("javaType", paramItem.prarmType);
          formItem.set("paramIndex", paramItem.prarmIndex);
          formItem.set("nameCh", paramItem.nameCh);
          formItem.set("description", paramItem.description);
          formItem.set("example", paramItem.example);
          formItem.set("defaultValue", paramItem.defaultValue);
          formItem.set("allowableValues", paramItem.allowableValues);
          formsArray.push(formItem);
        } else {
          // 没有 htmlType, 说明是个对象
          var prarmInfoArray = paramItem.prarmInfo;
          for(var j = 0; j < prarmInfoArray.length; j++){
            var prarmInfoItem = prarmInfoArray[j];
          }
        }
      }

      return(
        <Form>
          {
            objArray.map((item, index) => {
              return (
                <Form.Item key={'xxx' + index} label={'xxx' + index}>
                  {
                    this.buildFormItem(item)
                  }
                </Form.Item>
              )
            })
          }
        </Form>
      );
    } else {
      return (
        <h1>加载中...</h1>
      );
    }
    
  }


  render() {

    return (
      <div>
        <div>
          <h1>接口名称: {this.showApiName()}</h1>
        </div>
        <div>
          <Input
          id={'textId1'}
          htmlType={'text'}
          className="testInputClass"
          style={{ marginLeft: 5, width: 200 }}
          placeholder={'127.0.0.1'}
          data-test="123"
          />
          <Input
          id={'textId2'}
          htmlType={'text'}
          className="testInputClass"
          style={{ marginLeft: 5, width: 200 }}
          placeholder={'127.0.0.1'}
          data-test="456"
          />
          <Button
            type={'primary'}
            style={{ marginLeft: 10 }}
            onClick={ this.test }
          >
            加载接口列表
          </Button>

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