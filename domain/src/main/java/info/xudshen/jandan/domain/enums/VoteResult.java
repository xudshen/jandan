package info.xudshen.jandan.domain.enums;

/**
 * Created by xudshen on 16/2/26.
 */
public enum VoteResult {
    Thanks,
    Voted;

    public static VoteResult fromString(String s, VoteType voteType) {
        if (s.lastIndexOf("|") != -1 && s.lastIndexOf("|") != s.length() - 1) {
            String result = s.substring(s.lastIndexOf("|") + 1, s.length());
            switch (voteType) {
                case OO: {
                    return Integer.valueOf(result) == 1 ? Thanks : Voted;
                }
                case XX: {
                    return Integer.valueOf(result) == -1 ? Thanks : Voted;
                }
                default: {
                    return Voted;
                }
            }
        } else {
            return Voted;
        }
    }
}
