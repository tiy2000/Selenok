package com.hellofresh.pages;

import org.openqa.selenium.By;

public class InvitePage extends HelloFreshBasePage {

    private static final By BUTTON_SEND_INVITE =
            By.xpath("(//span[@data-translation-id='referrals.shared.form.submit-button.send-invite'])[2]");


    public InvitePage() {
        pageIdLocator = BUTTON_SEND_INVITE;
    }
}
