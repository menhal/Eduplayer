//
//  EduPlayer.h
//  
//
//  Updated by Tom Krones 2013-09-30.
//  Created by Peter Robinett on 2012-10-15.
//
//

#import <Cordova/CDV.h>
#import "EduPlayerViewController.h"

@interface EduPlayer : CDVPlugin {
  EduPlayerViewController *player;
  NSString *movie;
  NSString *callbackId;
}

- (void) play:(CDVInvokedUrlCommand*)command;

@end