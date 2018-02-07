const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const NotifierPlugin = require('webpack-notifier');

const isNoti = process.env.NOTI || 'off';
const isProduction = process.env.NODE_ENV === 'production';

const entry = {
  app: ['./src/index.js']
};

const output = {
  path: path.resolve(__dirname, 'dist'),
  filename: '[name]-[hash].js'
};

const rules = [
  {
    test: /\.js$/,
    exclude: [/node_modules/],
    loader: 'babel-loader'
  },
  {
    test:  /\.(handlebars|hbs)$/i,
    exclude: [/node_modules/],
    loader: 'handlebars-loader?helperDirs[]=' + __dirname + '/src/helpers'
  }
];


const plugins = [
  new CopyWebpackPlugin([
    { from: 'assets' },
  ], { copyUnmodified: false }),

  new HtmlWebpackPlugin({
    inject: true,
    title: 'woowahan project',
    template: 'src/index.html'
  }),

  new NotifierPlugin({ 
    title: 'woowahan project', 
    alwaysNotify: true 
  })
];

const webpackConfig = {
  entry, 
  output,
  plugins
};

webpackConfig.module = { rules };

if (isProduction) {

  plugins.push(new webpack.optimize.UglifyJsPlugin({
    compressor: {
      screw_ie8: true,
      warnings: false
    },
    output: {
      comments: false
    }
  }));
  
} else {  
  webpackConfig.devtool = 'cheap-eval-source-map';
}

process.noDeprecation = true;

module.exports = webpackConfig;
