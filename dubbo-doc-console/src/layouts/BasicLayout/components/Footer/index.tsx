import React from 'react';
import styles from './index.module.scss';

export default function Footer() {
  return (
    <p className={styles.footer}>
      <span className={styles.logo}>Dubbo Doc</span>
      <br />
      <span className={styles.copyright}>Mail: 213539@qq.com</span>
    </p>
  );
}