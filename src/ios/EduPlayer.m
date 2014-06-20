//
//  EduPlayer.m
//  
//
//  Updated by Tom Krones 2013-09-30.
//  Created by Peter Robinett on 2012-10-15.
//
//

#import "EduPlayer.h"
#import "MediaPlayer/MPMoviePlayerViewController.h"
#import "MediaPlayer/MPMoviePlayerController.h"
#import "EduPlayerViewController.h"
#import <Cordova/CDV.h>

@implementation EduPlayer
- (void) play:(CDVInvokedUrlCommand*)command
{
  movie = [command.arguments objectAtIndex:0];
  int start = [[command.arguments objectAtIndex:1] integerValue];
  callbackId = command.callbackId;
  NSRange range = [movie rangeOfString:@"http"];
  if(range.length > 0) {
    player = [[EduPlayerViewController alloc] initWithContentURL:[NSURL URLWithString:movie] andOrientation:YES];
    
  } else {
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *soundFilePath = [paths objectAtIndex:0];
    NSURL *fileURL = [NSURL fileURLWithPath:[soundFilePath stringByAppendingPathComponent:movie]];
    player = [[EduPlayerViewController alloc] initWithContentURL:fileURL andOrientation:YES];
  }
  if (player) {
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(MovieDidFinish:) name:MPMoviePlayerPlaybackDidFinishNotification object:nil];
    [self.viewController presentMoviePlayerViewControllerAnimated:player];
      player.moviePlayer.initialPlaybackTime = start;
  }
}

- (void)MovieDidFinish:(NSNotification *)notification {
    
    NSString *time = [NSString stringWithFormat:@"%f",player.moviePlayer.currentPlaybackTime];
    [player.moviePlayer stop];
    player.moviePlayer.initialPlaybackTime = -1.0;
    [[NSNotificationCenter defaultCenter] removeObserver:self
                                                  name:MPMoviePlayerPlaybackDidFinishNotification
                                                object:nil];
    
    NSDictionary *data = [[NSDictionary alloc] initWithObjectsAndKeys:time,@"time", nil];
    CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:data];
    [self success:result callbackId:callbackId];
  
}

- (void)dealloc {
  //[player release];
  //[movie release];
  //[super dealloc];
}

@end
