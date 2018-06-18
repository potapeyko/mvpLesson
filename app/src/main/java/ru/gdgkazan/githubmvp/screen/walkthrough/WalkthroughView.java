package ru.gdgkazan.githubmvp.screen.walkthrough;

import java.util.List;

import ru.gdgkazan.githubmvp.content.Benefit;


public interface WalkthroughView  {
    void openAuthActivityScreen();
    void setBenefits(List<Benefit> benefits);

    void showActionButtonText(int next_uppercase);

    void scrollToNextBenefit();

}
